/*
 * JimBot - Java IM Bot
 * Copyright (C) 2006-2010 JimBot project
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package ru.jimbot.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Enumeration;

/**
 * http://wiki.eclipse.org/Jetty/
 * http://wiki.eclipse.org/Jetty/Tutorial/Embedding_Jetty#Servlets
 * http://wiki.eclipse.org/Jetty/Tutorial
 * http://wiki.eclipse.org/Jetty/Feature
 * http://wiki.eclipse.org/Jetty/Tutorial/HttpClient
 * http://wiki.eclipse.org/Jetty/Tutorial/JAAS
 * http://wiki.eclipse.org/Jetty/Howto
 * 
 */
public class TestServlet extends HttpServlet {

    int redirectCount=0;
    
    protected void handleForm(HttpServletRequest request,
                              HttpServletResponse response)
        {
            HttpSession session = request.getSession(false);
            String action = request.getParameter("Action");
            String name =  request.getParameter("Name");
            String value =  request.getParameter("Value");

            if (action!=null)
            {
                if(action.equals("New Session"))
                {
                    session = request.getSession(true);
                    session.setAttribute("test","value");
                }
                else if (session!=null)
                {
                    if (action.equals("Invalidate"))
                        session.invalidate();
                    else if (action.equals("Set") && name!=null && name.length()>0)
                        session.setAttribute(name,value);
                    else if (action.equals("Remove"))
                        session.removeAttribute(name);
                }
            }
        }

    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response)
        throws ServletException, IOException
    {
        handleForm(request,response);
        String nextUrl = getURI(request)+"?R="+redirectCount++;
        String encodedUrl=response.encodeRedirectURL(nextUrl);
        response.sendRedirect(encodedUrl);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
//        response.setContentType("text/html");
//        response.setStatus(HttpServletResponse.SC_OK);
//        response.getWriter().println("<h1>Hello World</h1>");
//        response.getWriter().println("session=" + request.getSession(true).getId());
        
        response.setContentType("text/html");

//        HttpSession session = request.getSession(getURI(request).indexOf("new")>0);
        HttpSession session = request.getSession(true);
        try
        {
            if (session!=null)
                session.isNew();
        }
        catch(IllegalStateException e)
        {
            session=null;
        }

        PrintWriter out = response.getWriter();
        out.println("<h1>Session Dump Servlet:</h1>");
        out.println("<form action=\""+response.encodeURL(getURI(request))+"\" method=\"post\">");

        if (session==null)
        {
            out.println("<H3>No Session</H3>");
            out.println("<input type=\"submit\" name=\"Action\" value=\"New Session\"/>");
        }
        else
        {
            try
            {
                out.println("<b>ID:</b> "+session.getId()+"<br/>");
                out.println("<b>New:</b> "+session.isNew()+"<br/>");
                out.println("<b>Created:</b> "+new Date(session.getCreationTime())+"<br/>");
                out.println("<b>Last:</b> "+new Date(session.getLastAccessedTime())+"<br/>");
                out.println("<b>Max Inactive:</b> "+session.getMaxInactiveInterval()+"<br/>");
                out.println("<b>Context:</b> "+session.getServletContext()+"<br/>");


                Enumeration keys=session.getAttributeNames();
                while(keys.hasMoreElements())
                {
                    String name=(String)keys.nextElement();
                    String value=""+session.getAttribute(name);

                    out.println("<b>"+name+":</b> "+value+"<br/>");
                }

                out.println("<b>Name:</b><input type=\"text\" name=\"Name\" /><br/>");
                out.println("<b>Value:</b><input type=\"text\" name=\"Value\" /><br/>");

                out.println("<input type=\"submit\" name=\"Action\" value=\"Set\"/>");
                out.println("<input type=\"submit\" name=\"Action\" value=\"Remove\"/>");
                out.println("<input type=\"submit\" name=\"Action\" value=\"Refresh\"/>");
                out.println("<input type=\"submit\" name=\"Action\" value=\"Invalidate\"/><br/>");

                out.println("</form><br/>");

                if (request.isRequestedSessionIdFromCookie())
                    out.println("<P>Turn off cookies in your browser to try url encoding<BR>");

                if (request.isRequestedSessionIdFromURL())
                    out.println("<P>Turn on cookies in your browser to try cookie encoding<BR>");
                out.println("<a href=\""+response.encodeURL(request.getRequestURI()+"?q=0")+"\">Encoded Link</a><BR>");

            }
            catch (IllegalStateException e)
            {
                e.printStackTrace();
            }
        }
    }

    private String getURI(HttpServletRequest request)
    {
        String uri=(String)request.getAttribute("javax.servlet.forward.request_uri");
        if (uri==null)
            uri=request.getRequestURI();
        return uri;
    }
}
