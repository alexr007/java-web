package org.alexr.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

public class ExploreRequestServlet extends HttpServlet {
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    // http://127.0.0.1:9001/ex?x=5
    String x = req.getParameter("x");
    // http://127.0.0.1:9001/ex?x=5&x=6&y=7
    Map<String, String[]> all_params = req.getParameterMap();
    String[] xes = all_params.get("x");
    String method = req.getMethod(); // GET, POST, ...
    // request headers
    Enumeration<String> headerNames = req.getHeaderNames();
    // cookies
    Cookie[] cookies = req.getCookies();
    // http://127.0.0.1:9001/ex/123/?x=5&x=6&y=7
    String pathInfo = req.getPathInfo();           // '/123/' - part between the mapping path and parameters
    String queryString = req.getQueryString();     // 'x=5&x=6&y=7+++' - whole unparsed string after '?'
    String contextPath = req.getContextPath();     // see documentation
    String servletPath = req.getServletPath();     // '/ex' - how it was mapped
    StringBuffer requestURL = req.getRequestURL(); // http://127.0.0.1:9001/ex/123/
    String requestURI = req.getRequestURI();       // '/ex/123/'


    try (PrintWriter w = resp.getWriter()) {
      w.printf("one line x=%s\n", x);
      for (String s: xes) {
        w.printf("x=%s\n", s);
      }
      w.printf("getProtocol:%s\ngetServerName:%s\ngetServerPort%s\n" +
              "getServletPath:%s\ngetContextPath: %s\n" +
              "getPathInfo:%s\ngetQueryString:%s\n" +
              "getRequestURL:%s\ngetRequestURI:%s\n" +
              "%s",
          req.getProtocol(),    //  HTTP/1.1
          req.getServerName(),  //  localhost
          req.getServerPort(),  //  9001

          req.getServletPath(), //  /a
          req.getContextPath(), //  EMPTY

          req.getPathInfo(),    //  /1/2/3/4/5 - all between servlet mapping "/a" and parameters or NULL
          req.getQueryString(), //  x=5&y=+++ or NULL

          req.getRequestURL(),  //  http://localhost:9001/a/1/2/3/4/5
          req.getRequestURI(),  //  /a/1/2/3/4/5
          ""
      );
    }


  }
}
