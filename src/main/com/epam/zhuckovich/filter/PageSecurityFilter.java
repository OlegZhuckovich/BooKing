package com.epam.zhuckovich.filter;

import com.epam.zhuckovich.entity.User;
import com.epam.zhuckovich.entity.UserType;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter
public class PageSecurityFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(PageSecurityFilter.class);

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
    public void init(FilterConfig filterConfig) throws ServletException {
        loginPage = filterConfig.getInitParameter(LOGIN_PAGE);
        administratorMenu = filterConfig.getInitParameter(ADMINISTRATOR_MENU);
        librarianMenu = filterConfig.getInitParameter(LIBRARIAN_MENU);
        memberMenu = filterConfig.getInitParameter(MEMBER_MENU);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        HttpSession session = httpRequest.getSession();
        String currentPage  = httpRequest.getRequestURI();
        try {
            User currentUser = (User) session.getAttribute(USER);
            if(currentUser != null){
                UserType userType = currentUser.getUserType();
                if(userType == UserType.ADMINISTRATOR && !currentPage.contains(ADMINISTRATOR_URI) && !currentPage.contains(COMMON_URI)){
                    httpResponse.sendRedirect(httpRequest.getContextPath() + administratorMenu);
                } else if(userType == UserType.LIBRARIAN && !currentPage.contains(LIBRARIAN_URI) && !currentPage.contains(COMMON_URI)){
                    httpResponse.sendRedirect(httpRequest.getContextPath() + librarianMenu);
                } else if(userType == UserType.MEMBER && !currentPage.contains(MEMBER_URI) && !currentPage.contains(COMMON_URI)){
                    httpResponse.sendRedirect(httpRequest.getContextPath() + memberMenu);
                } else {
                    filterChain.doFilter(servletRequest, servletResponse);
                }

            }
        } catch (ClassCastException e){
            LOGGER.log(Level.ERROR,"ClassCastException was occurred during the filtering of the request");
            httpResponse.sendRedirect(httpRequest.getContextPath() + loginPage);
        }
    }

    @Override
    public void destroy() {}
}