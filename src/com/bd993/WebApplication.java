package com.bd993;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;

import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;
import org.glassfish.jersey.servlet.ServletProperties;

public class WebApplication extends ResourceConfig {

	public WebApplication() {
        register(Service.class);
        register(JspMvcFeature.class);
        register(MultiPartFeature.class);
        property("jersey.config.servlet.filter.forwardOn404", true);
        property("jersey.config.servlet.filter.staticContentRegex", "/*.css|/*.js");
        property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp/");
        property(ServletProperties.FILTER_FORWARD_ON_404, true);
        try {
          System.setProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
        }
        catch (Throwable e) {
          e.printStackTrace();
        }
    }
}
