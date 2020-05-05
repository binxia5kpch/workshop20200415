package shardingsphere.workshop.mysql.proxy.todo.executor;

import io.netty.channel.ChannelHandlerContext;

/**
 * 执行sql的基类
 */
public interface SQLExecutor {

    /**
     * 执行sql
     * @param context
     */
    void execute(final ChannelHandlerContext context);
}
