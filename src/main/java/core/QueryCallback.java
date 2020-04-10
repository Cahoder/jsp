package core;

import java.sql.SQLException;

public interface QueryCallback {

    /**
     * @return 提供一个执行具体Query操作的接口,通过回调完成具体操作
     * @throws SQLException SQL异常
     */
    Object doExecute() throws SQLException;
}
