/*
package com.abirami.web;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class HelloServletTest {
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    @Mock private RequestDispatcher requestDispatcher;
    @Mock ServletContext ctx;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(ctx.getResourceAsStream(Mockito.anyString())).thenAnswer(new Answer<InputStream>() {
            String path = HelloServletTest.class.getProtectionDomain().getCodeSource().getLocation().getPath()
                    + "../../src/main/webapp";

            @Override
            public InputStream answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                String relativePath = (String) args[0];
                InputStream is = new FileInputStream(path + relativePath);
                return is;
            }
        });

    }

    @Test
    public void doGet() throws Exception {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        when(response.getWriter()).thenReturn(printWriter);

        new HelloServlet().doGet(request, response);

        assertEquals("Hello, World!", stringWriter.toString());
    }
 

    @Test
    public void doPost() throws Exception {
        when(request.getParameter("productName")).thenReturn("Calendar1");
        when(request.getParameter("productDesc")).thenReturn("Calendar 1 Description");
        when(request.getRequestDispatcher("products.jsp"))
            .thenReturn(requestDispatcher);

        new HelloServlet().doPost(request, response);

        //verify(request).setAttribute("user", "Dolly");
        verify(requestDispatcher).forward(request,response);
    }
    
}
*/