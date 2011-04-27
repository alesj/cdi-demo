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

package com.acme.test.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jboss.shrinkwrap.api.asset.Asset;

/**
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public class BeansXml implements Asset
{
   private List<Class<?>> alternatives = new ArrayList<Class<?>>();
   private List<Class<?>> interceptors = new ArrayList<Class<?>>();
   private List<Class<?>> decorators = new ArrayList<Class<?>>();
   private List<Class<?>> stereotypes = new ArrayList<Class<?>>();

   public BeansXml()
   {

   }

   public BeansXml alternatives(Class<?>... alternatives)
   {
      this.alternatives.addAll(Arrays.asList(alternatives));
      return this;
   }

   public BeansXml interceptors(Class<?>... interceptors)
   {
      this.interceptors.addAll(Arrays.asList(interceptors));
      return this;
   }

   public BeansXml decorators(Class<?>... decorators)
   {
      this.decorators.addAll(Arrays.asList(decorators));
      return this;
   }

   public BeansXml stereotype(Class<?>... stereotypes)
   {
      this.stereotypes.addAll(Arrays.asList(stereotypes));
      return this;
   }

   public InputStream openStream()
   {
      StringBuilder xml = new StringBuilder();
      xml.append("<beans>\n");
      appendAlternatives(alternatives, stereotypes, xml);
      appendSection("interceptors", "class", interceptors, xml);
      appendSection("decorators", "class", decorators, xml);
      xml.append("</beans>");

      return new ByteArrayInputStream(xml.toString().getBytes());
   }

   private void appendAlternatives(List<Class<?>> alternatives, List<Class<?>> stereotypes, StringBuilder xml)
   {
      if (alternatives.size() > 0 || stereotypes.size() > 0)
      {
         xml.append("<").append("alternatives").append(">\n");
         appendClasses("class", alternatives, xml);
         appendClasses("stereotype", stereotypes, xml);
         xml.append("</").append("alternatives").append(">\n");
      }
   }

   private void appendSection(String name, String subName, List<Class<?>> classes, StringBuilder xml)
   {
      if (classes.size() > 0)
      {
         xml.append("<").append(name).append(">\n");
         appendClasses(subName, classes, xml);
         xml.append("</").append(name).append(">\n");
      }
   }

   private void appendClasses(String name, List<Class<?>> classes, StringBuilder xml)
   {
      for (Class<?> clazz : classes)
      {
         xml.append("<").append(name).append(">").append(clazz.getName()).append("</").append(name).append(">\n");
      }
   }
}