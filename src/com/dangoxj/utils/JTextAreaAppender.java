package com.dangoxj.utils;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * Created by zhangshouzhi on 13-12-1.
 */
public class JTextAreaAppender extends AppenderSkeleton
{
    javax.swing.JTextArea jText = null;

    public JTextAreaAppender(Layout layout, javax.swing.JTextArea jText)
    {
        this.layout = layout;
        this.jText = jText;
    }

    public JTextAreaAppender(javax.swing.JTextArea jText)
    {
        this.layout = new org.apache.log4j.PatternLayout("%p [%t] %c  - %m%n");
        this.jText = jText;
    }

    public void setJText(javax.swing.JTextArea jText)
    {
        this.jText = jText;
    }

    protected void append(LoggingEvent event)
    {
        if(this.jText == null)
            return ;
        this.subAppend(event);
    }
    public boolean requiresLayout()
    {
        return true;
    }

    public synchronized void close()
    {
        if (this.closed)
        {
            return;
        }
        this.closed = true;

    }

    public void subAppend(LoggingEvent event)
    {
        synchronized(jText)
        {
            this.jText.append(this.layout.format(event));
        }

    }
}
