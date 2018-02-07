package com.epam.zhuckovich.command;

import com.epam.zhuckovich.controller.Router;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>A functional interface designed to perform various actions
 * defined in the class CommandFactory.</p>
 * @author      Oleg Zhuckovich
 * @version     %I%, %G%
 * @see         CommandFactory
 * @since       1.0
 */

@FunctionalInterface
public interface Command {
    Router execute(HttpServletRequest request);
}
