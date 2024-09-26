package ru.roznov.servlets_2.objects.clients;

import ru.roznov.servlets_2.controler.command.Page;
import ru.roznov.servlets_2.controler.command.RedirectEnum;

public enum RoleEnum {
    DRIVER {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/driver.jsp");
        }
    },

    STOREKEEPER {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/keeper.jsp");
        }
    },

    ADMIN {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/adm.jsp");
        }
    },

    MODERATOR {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/moder.jsp");
        }
    },

    UNKNOWN {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/login.jsp");
        }
    },

    BLOCKED {
        {
            this.page = new Page(RedirectEnum.FORWARD, "/WEB-INF/view/pageBlockedClient.jsp");
        }
    };


    public Page page;

    public Page getPage() {
        return this.page;
    }
}
