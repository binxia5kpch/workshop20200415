package shardingsphere.workshop.mysql.proxy.todo;

import com.google.common.base.Preconditions;
import io.netty.channel.ChannelHandlerContext;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLPacketPayload;

public class CheckSqlAndParam {

    public static void checkSql(ChannelHandlerContext context, MySQLPacketPayload payload) {
        //COM_INIT_DB 切换数据库
        Preconditions.checkState(0x03 == payload.readInt1(), "only support COM_QUERY command type");
        String sql = payload.readStringEOF();
        String sqlstrs[] = sql.split(" ");
        Preconditions.checkArgument(null !=sqlstrs && sqlstrs.length>1, "unsupport sql expression");
        //.........可以做一大堆检查
    }
}
