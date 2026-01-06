package com.yupi.aicodehelper.ai.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.transport.http.HttpMcpTransport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class McpConfig {

    @Value("${bigmodel.api-key}")
    private String apiKey;

    @Bean
    public McpToolProvider mcpToolProvider() {
        //和mcp服务通讯
        HttpMcpTransport transport = new HttpMcpTransport.Builder()
                .sseUrl("https://open.bigmodel.cn/api/mcp/web_search/sse?Authorization=" + apiKey)
                .logRequests(true) //开启日志
                .logResponses(true)
                .build();
        //创建mcp客户端
        DefaultMcpClient mcpClient = new DefaultMcpClient.Builder()
                .key("dscMcpClient")
                .transport(transport)
                .build();
        //从MCP客户端获取工具
        McpToolProvider toolProvider = McpToolProvider
                .builder()
                .mcpClients(mcpClient)
                .build();
        return toolProvider;
    }
}
