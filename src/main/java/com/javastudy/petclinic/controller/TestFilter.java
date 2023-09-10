package com.javastudy.petclinic.controller;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@WebFilter("/TestServlet")
public class TestFilter implements Filter {

    public TestFilter(){

    }

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.getWriter().write("before...");
        chain.doFilter(req, resp);
        resp.getWriter().write("after...");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}