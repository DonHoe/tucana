package com.zk.device.util;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public class VelocityUtil {

    /**
     * 渲染模板
     *
     * @param template 模板地址
     * @param map      数据
     * @return
     */
    public static String render(String template, Map<String, Object> map) throws IOException {
        VelocityInitializer.initVelocity();
        Template t = Velocity.getTemplate(template);
        VelocityContext ctx = new VelocityContext();
        map.entrySet().forEach(p -> ctx.put(p.getKey(), p.getValue()));
        StringWriter sw = new StringWriter();
        t.merge(ctx, sw);
        String result = sw.toString();
        sw.close();
        return result;
    }

}
