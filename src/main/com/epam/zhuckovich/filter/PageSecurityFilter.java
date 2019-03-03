package com.epam.zhuckovich.filter;

import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.User.Role;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter
public class PageSecurityFilter implements Filter {

    private final static String USER = "user";
    private final static String LOGIN_PAGE = "loginPage";
    private final static String ADMINISTRATOR_MENU = "administratorMenu";
    private final static String LIBRARIAN_MENU = "librarianMenu";
    private final static String MEMBER_MENU = "memberMenu";
    private final static String ADMINISTRATOR_URI = "/jsp/administrator/";
    private final static String LIBRARIAN_URI = "/jsp/librarian/";
    private final static String MEMBER_URI = "/jsp/member/";
    private final static String COMMON_URI = "/jsp/common/";
    private String loginPage;
    private String administratorMenu;
    private String librarianMenu;
    private String memberMenu;

    @Override
    public void init(FilterConfig filterConfig) {
        loginPage = filterConfig.getInitParameter(LOGIN_PAGE);
        administratorMenu = filterConfig.getInitParameter(ADMINISTRATOR_MENU);
        librarianMenu = filterConfig.getInitParameter(LIBRARIAN_MENU);
        memberMenu = filterConfig.getInitParameter(MEMBER_MENU);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        var httpRequest = (HttpServletRequest) servletRequest;
        var httpResponse = (HttpServletResponse) servletResponse;
        var currentPage  = httpRequest.getRequestURI();
        Object user =  httpRequest.getSession().getAttribute(USER);
        if(user instanceof String){
            if(((String) user).isEmpty()) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + loginPage);
            }
        } else {
            User currentUser = (User) user;
            Role role = currentUser.getRole();
            if(role == Role.ADMINISTRATOR && !currentPage.contains(ADMINISTRATOR_URI) && !currentPage.contains(COMMON_URI)){
                httpResponse.sendRedirect(httpRequest.getContextPath() + administratorMenu);
            } else if(role == Role.LIBRARIAN && !currentPage.contains(LIBRARIAN_URI) && !currentPage.contains(COMMON_URI)){
                httpResponse.sendRedirect(httpRequest.getContextPath() + librarianMenu);
            } else if(role == Role.MEMBER && !currentPage.contains(MEMBER_URI) && !currentPage.contains(COMMON_URI)){
                httpResponse.sendRedirect(httpRequest.getContextPath() + memberMenu);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {}
}
