package com.sceneforge.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

import com.sceneforge.agent.AgentTaskOrchestrator;
import com.sceneforge.ai.AiProviderAdapterRegistry;
import com.sceneforge.ai.provider.CustomProviderAdapter;
import com.sceneforge.common.BusinessException;
import com.sceneforge.common.ErrorCode;
import com.sceneforge.config.SceneForgeProperties;
import com.sceneforge.repository.AiTaskRepository;
import com.sceneforge.security.CurrentUserProvider;
import com.sceneforge.task.AiTaskEventPublisher;
import com.sceneforge.task.AiTaskQueue;
import com.sceneforge.task.ModelTaskExecutionDispatcher;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.reactive.TransactionalOperator;

/**
 * AI模型目录访问权限测试。
 *
 * @author   zhenglin.cn.cq@gmail.com
 * @date     2026-07-24 23:09
 */
class AiTaskModelAccessTest {

    /**
     * 未登录用户查询模型目录时应被拒绝，并且不得读取平台模型配置。
     *
     * @return void 无返回值
     */
    @Test
    void shouldRejectModelCatalogRequestWithoutAuthenticatedUser() {
        PersistenceService persistenceService = mock(PersistenceService.class);
        org.mockito.Mockito.when(persistenceService.requireStorageWritable(org.mockito.ArgumentMatchers.anyLong())).thenReturn(reactor.core.publisher.Mono.empty());
        AiTaskService service = new AiTaskService(com.sceneforge.TeamTestSupport.taskScope(), mock(AiTaskRepository.class), new CurrentUserProvider(), mock(SceneForgeProperties.class), persistenceService, com.sceneforge.MediaAccessTestSupport.echo(), mock(AiTaskEventPublisher.class), mock(AiTaskQueue.class), mock(ModelTaskExecutionDispatcher.class), mock(AgentTaskOrchestrator.class), mock(AiProviderAdapterRegistry.class), mock(CustomProviderAdapter.class), mock(CreditService.class), mock(TransactionalOperator.class));

        BusinessException exception = Assertions.assertThrows(BusinessException.class,
                () -> service.listModels().block());

        Assertions.assertEquals(ErrorCode.TOKEN_INVALID, exception.getCode());
        verifyNoInteractions(persistenceService);
    }
}
