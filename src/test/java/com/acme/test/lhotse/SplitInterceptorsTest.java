/*
* JBoss, Home of Professional Open Source
* Copyright $today.year Red Hat Inc. and/or its affiliates and other
* contributors as indicated by the @author tags. All rights reserved.
* See the copyright.txt in the distribution for a full listing of
* individual contributors.
* 
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
* 
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
* 
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/

package com.acme.test.lhotse;

import javax.inject.Inject;

import com.acme.test.lhotse.fst.TDAO;
import com.acme.test.lhotse.fst.TxInterceptor;
import com.acme.test.lhotse.snd.CDAO;
import com.acme.test.lhotse.snd.Client;
import com.acme.test.utils.BeansXml;
import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
@RunWith(Arquillian.class)
public class SplitInterceptorsTest
{
   @Deployment
   public static Archive<?> deploy()
   {
      BeansXml beansXml = new BeansXml().interceptors(TxInterceptor.class);

      WebArchive web = ShrinkWrap.create(WebArchive.class).addPackage(SplitInterceptorsTest.class.getPackage());
      web.addAsWebInfResource(beansXml, "beans.xml");

      JavaArchive fst = ShrinkWrap.create(JavaArchive.class);
      fst.addPackage(TDAO.class.getPackage());
      fst.addAsManifestResource(beansXml, "beans.xml");

      JavaArchive snd = ShrinkWrap.create(JavaArchive.class);
      snd.addPackage(CDAO.class.getPackage());
      snd.addAsManifestResource(beansXml, "beans.xml");

      web.addAsLibraries(fst, snd);

      return web;
   }

   @Inject EDAO edao;

   @Test
   public void testInterceptors(CDAO cdao) throws Exception
   {
      TxInterceptor.used = false;
      Client c = new Client();
      Assert.assertTrue(cdao.save(c));
      Assert.assertTrue(TxInterceptor.used);

      TxInterceptor.used = false;
      Enigma e = new Enigma();
      Assert.assertEquals(42, edao.solve(e));
      Assert.assertTrue(TxInterceptor.used);
   }
}
