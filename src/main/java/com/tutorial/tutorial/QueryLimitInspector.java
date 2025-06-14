package com.tutorial.tutorial;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class QueryLimitInspector implements StatementInspector{

    @Override
    public String inspect(String sql) {
        if(sql.toLowerCase().startsWith("select") && sql.toLowerCase().contains("limit")){
            return sql +" LIMIT 50";
        }
        return sql;
    }

}
