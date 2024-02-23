/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alipay.sofa.boot.spring.namespace.handler;

import com.alipay.sofa.boot.log.InfraLoggerFactory;
import com.alipay.sofa.boot.spring.namespace.spi.SofaBootTagNameSupport;
import org.slf4j.Logger;
import org.springframework.beans.factory.xml.BeanDefinitionDecorator;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import java.util.ServiceLoader;

/**
 * SofaBootNamespaceHandler
 * SofaBootNamespaceHandler 在初始化阶段，基于 SPI 机制查找所有标签解析器，调用 registerTagParser 方法注册标签解析器。
 *
 * @author yangguanchao
 * @author qilong.zql
 * @since 2018/04/08
 */
public class SofaBootNamespaceHandler extends NamespaceHandlerSupport {

    private static final Logger logger = InfraLoggerFactory.getLogger(SofaBootNamespaceHandler.class);

    @Override
    public void init() {
        // SofaBootNamespaceHandler 在初始化阶段，基于 SPI 机制查找所有标签解析器，调用 registerTagParser 方法注册标签解析器。
        ServiceLoader<SofaBootTagNameSupport> serviceLoaderSofaBoot = ServiceLoader.load(SofaBootTagNameSupport.class);
        serviceLoaderSofaBoot.forEach(this::registerTagParser);
    }

    /**
     * 注册标签解析器时，调用解析器的 supportTagName 方法获取标签名，将解析器与标签名关联起来。
     *
     * @param tagNameSupport
     */
    private void registerTagParser(SofaBootTagNameSupport tagNameSupport) {
        if (tagNameSupport instanceof BeanDefinitionParser) {
            registerBeanDefinitionParser(tagNameSupport.supportTagName(), (BeanDefinitionParser) tagNameSupport);
        } else if (tagNameSupport instanceof BeanDefinitionDecorator) {
            registerBeanDefinitionDecoratorForAttribute(tagNameSupport.supportTagName(), (BeanDefinitionDecorator) tagNameSupport);
        } else {
            logger.error(tagNameSupport.getClass() + " tag name supported [" + tagNameSupport.supportTagName() + "] parser are not instance of " + BeanDefinitionParser.class + "or " + BeanDefinitionDecorator.class);
        }
    }
}
