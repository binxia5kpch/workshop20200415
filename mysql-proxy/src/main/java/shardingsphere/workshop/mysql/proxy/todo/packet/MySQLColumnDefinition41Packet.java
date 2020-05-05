
package shardingsphere.workshop.mysql.proxy.todo.packet;

import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLPacket;
import shardingsphere.workshop.mysql.proxy.fixture.packet.MySQLPacketPayload;
import shardingsphere.workshop.mysql.proxy.fixture.packet.constant.MySQLColumnType;
import shardingsphere.workshop.mysql.proxy.fixture.packet.constant.MySQLServerInfo;
import lombok.Getter;

/**
 * Column definition above MySQL 4.1 packet protocol.
 *
 * @see <a href="https://dev.mysql.com/doc/internals/en/com-query-response.html#packet-Protocol::ColumnDefinition41">ColumnDefinition41</a>
 * @see <a href="https://mariadb.com/kb/en/library/resultset/#column-definition-packet">Column definition packet</a>
 */
public final class MySQLColumnDefinition41Packet implements MySQLPacket {
    
    private static final String CATALOG = "def";
    
    private static final int NEXT_LENGTH = 0x0c;
    
    @Getter
    private final int sequenceId;
    
    private final int characterSet;
    
    private final int flags;
    
    private final String schema;
    
    private final String table;
    
    private final String orgTable;
    
    private final String name;
    
    private final String orgName;
    
    private final int columnLength;
    
    private final MySQLColumnType columnType;
    
    private final int decimals;
    
    public MySQLColumnDefinition41Packet(final int sequenceId, final int flags, final String schema, final String table, final String orgTable,
                                         final String name, final String orgName, final int columnLength, final MySQLColumnType columnType, final int decimals) {
        /**
         * 包的序号，表示第几个包
         */
        this.sequenceId = sequenceId;
        this.characterSet = MySQLServerInfo.CHARSET;
        this.flags = flags; //默认值0
        this.schema = schema;  //数据库名
        this.table = table;    //表名（别名）
        this.orgTable = orgTable;  //原始表名
        this.name = name;    //列名
        this.orgName = orgName;  //原始列名
        this.columnLength = columnLength;  //列的长度
        this.columnType = columnType;   //列的类型
        this.decimals = decimals;   //是否是浮点型
    }
    
    @Override
    public void write(final MySQLPacketPayload payload) {
        payload.writeStringLenenc(CATALOG);
        payload.writeStringLenenc(schema);
        payload.writeStringLenenc(table);
        payload.writeStringLenenc(orgTable);
        payload.writeStringLenenc(name);
        payload.writeStringLenenc(orgName);
        payload.writeIntLenenc(NEXT_LENGTH);
        payload.writeInt2(characterSet);
        payload.writeInt4(columnLength);
        payload.writeInt1(columnType.getValue());
        payload.writeInt2(flags);
        payload.writeInt1(decimals);
        payload.writeReserved(2);
    }
}
