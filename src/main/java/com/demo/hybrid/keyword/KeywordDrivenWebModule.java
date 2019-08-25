package com.demo.hybrid.keyword;

import com.demo.hybrid.core.listeners.CleanUpListener;
import com.demo.hybrid.core.ifaces.CommandHandler;
import com.demo.hybrid.core.utils.ExcelReader;
import com.demo.hybrid.core.utils.ScopePropertiesReader;
import com.demo.hybrid.web.WebUiScopeMiddleware;
import com.demo.hybrid.web.handlers.commands.*;
import com.demo.hybrid.web.providers.WebDriverProvider;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.matcher.Matchers;
import com.google.inject.name.Names;
import org.openqa.selenium.WebDriver;

public class KeywordDrivenWebModule extends AbstractModule {

    @Override
    protected void configure(){

        // Common
        bind(ExcelReader.class);
        bind(TestGenerator.class);
        bind(ScopePropertiesReader.class).in(Scopes.SINGLETON);

        // Hooks
        bindListener(Matchers.any(), new CleanUpListener());

        // Web-UI related
        bind(WebDriver.class).toProvider(WebDriverProvider.class).in(Scopes.SINGLETON);
        bind(WebUiScopeMiddleware.class);

        // Web-UI command handlers
        // Actually, it looks like configuration, and it's not so good that we have it set up in a few places (here and in command handlers)
        // But as far as it was decided to play with Guice - leaving it as it is
        bind(CommandHandler.class).annotatedWith(Names.named("go to")).to(GoTo.class);
        bind(CommandHandler.class).annotatedWith(Names.named("input")).to(Input.class);
        bind(CommandHandler.class).annotatedWith(Names.named("click")).to(Click.class);
        bind(CommandHandler.class).annotatedWith(Names.named("select")).to(Select.class);
        bind(CommandHandler.class).annotatedWith(Names.named("find first in")).to(Find.class);

        // Queries

    }
}
