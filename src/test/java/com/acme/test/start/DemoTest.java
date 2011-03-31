package com.acme.test.start;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RunWith(Arquillian.class)
public class DemoTest
{
   @Deployment
   public static Archive myApp()
   {
      JavaArchive archive = ShrinkWrap.create(JavaArchive.class)
            .addPackage(DemoTest.class.getPackage())
            .addResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
      System.out.println("archive = " + archive.toString(true));
      return archive;
   }

   @Inject
   MyBean startingPoint;

   @Test
   public void testFirstTest() throws Exception
   {
      Assert.assertNotNull(startingPoint);
      Assert.assertNotNull(startingPoint.getManager());
   }
}
