package com.example.Database

import com.zaxxer.hikari.HikariConfig

class DatabaseFactory {
    fun init(){
        val config = HikariConfig().apply {
            jdbcUrl = "jdbc:mysql://localhost:3306/GeoPulse"
            driverClassName = "com.mysql.cj.jdbc.Driver"
            username = "root"
            password = "Rishav@2025"
            maximumPoolSize = 10
            connectionTimeout = 30000
            idleTimeout = 600000
            maxLifetime = 1800000
            poolName = "KTORPool"
            addDataSourceProperty("cachePrepStmts", "true")
            addDataSourceProperty("prepStmtCacheSize", "250")
            addDataSourceProperty("prepStmtCacheSqlLimit", "2048")
        }
    }
}