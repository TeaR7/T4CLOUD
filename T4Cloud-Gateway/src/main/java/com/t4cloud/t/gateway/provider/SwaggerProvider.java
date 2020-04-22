package com.t4cloud.t.gateway.provider;

import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 服务提供
 *
 * <p>
 * --------------------
 *
 * @author TeaR
 * @date 2020/2/14 0:34
 */
@Component
@Primary
@AllArgsConstructor
public class SwaggerProvider implements SwaggerResourcesProvider {

    private static final String API_URI = "/v2/api-docs";
    private final RouteLocator routeLocator;

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList<>();
        List<String> routes = new ArrayList<>();
        //取出gateway的route
        routeLocator.getRoutes().subscribe(route -> routes.add(route.getId()));

        //自动添加文档
        for (String route : routes) {
            if ((!route.contains("T4Cloud-Gateway")) && route.contains("T4Cloud")) {
                String[] location = route.split("_");
                if (location.length > 1) {
                    resources.add(swaggerResource(location[1] + "相关" , "/" + location[1]));
                }
            }
        }
        //手动添加文档
//        resources.add(swaggerResource("用户中心", "/T4Cloud-User"));
//        resources.add(swaggerResource("系统相关", "/T4Cloud-System"));
        return resources;
    }

    private SwaggerResource swaggerResource(String name, String location) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location.concat(API_URI));
        swaggerResource.setSwaggerVersion("T4CLOUD.SR1");
        return swaggerResource;
    }
}