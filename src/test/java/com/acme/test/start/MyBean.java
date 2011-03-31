package com.acme.test.start;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@ApplicationScoped
public class MyBean
{
   @Inject
   BeanManager manager;

   public BeanManager getManager()
   {
      return manager;
   }
}
