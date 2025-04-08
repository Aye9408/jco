package com.szlanyou.jco;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.JCoParameterList;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SapFunctionTest {

    @Autowired
    private SapUtils sapUtils;

    @Test
    public void testCallZFUN_SSF_RPA_005() throws Exception {
        // 获取 SAP 连接
        JCoDestination destination = sapUtils.getJcoConnection();

        // 获取函数对象
        JCoFunction function = sapUtils.getJCoFunction(destination, "ZFUN_SSF_RPA_005");

        // 设置参数 IV1 = X
        JCoParameterList importParams = function.getImportParameterList();
        importParams.setValue("IV1", "X");

        // 调用函数
        function.execute(destination);


        // 可选：打印返回参数
        if (function.getExportParameterList() != null) {
            System.out.println("返回结果：");
            function.getExportParameterList().forEach(field -> {
                System.out.println(field.getName() + " = " + field.getString());
            });
        }
    }
}
