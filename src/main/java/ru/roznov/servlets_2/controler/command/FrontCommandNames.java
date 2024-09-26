package ru.roznov.servlets_2.controler.command;

import ru.roznov.servlets_2.controler.command.realisation.*;

public enum FrontCommandNames {
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    SHOW_ADD_USER {
        {
            this.command = new ShowAddUserCommand();
        }
    },
    ADD_USER {

        {
            this.command = new AddUserCommand();
        }
    },
    CHANGE_USER {{
        this.command = new ChangeUserCommand();
    }},
    SHOW_FUNDED_USER {{
        this.command = new ShowFundedUserCommand();
    }},
    SHOW_SEARCH_USER {{
        this.command = new ShowSearchUserCommand();
    }},
    SHOW_DELETE_USER {{
        this.command = new ShowDeleteUserCommand();
    }},
    DELETE_USER {{
        this.command = new DeleteUserCommand();
    }},
    SHOW_CLIENTS {{
        this.command = new ShowClientsCommand();
    }},
    SHOW_ENTRY_REQUEST {{
        this.command = new ShowEntryRequestCommand();
    }},
    ENTRY_REQUEST {{
        this.command = new EntryRequestCommand();
    }},
    SHOW_HANDLING_REQUEST {{
        this.command = new ShowHandlingRequestCommand();
    }},
    HANDLING_REQUEST {{
        this.command = new HandlingRequestCommand();
    }},
    REJECT_REQUEST {{
        this.command = new RejectRequestCommand();
    }},
    CONFIRM_REQUEST {{
        this.command = new ConfirmRequestCommand();
    }},
    SHOW_HANDLE_REQUESTS_KEEPER {{
        this.command = new ShowHandleRequestsKeeperCommand();
    }},
    SHOW_ADD_PRODUCT {{
        this.command = new ShowAddProductCommand();
    }},
    ADD_PRODUCT {{
        this.command = new AddProductCommand();
    }},
    SHOW_BLOCK_CLIENT {{
        this.command = new ShowBlockClientCommand();
    }},
    BLOCK_CLIENT {{
        this.command = new BlockClientCommand();
    }},
    SHOW_UNBLOCK_CLIENT {{
        this.command = new ShowUnBlockClientCommand();
    }},
    LOGIN {{
        this.command = new LoginCommand();
    }},
    SHOW_SUCCESS {{
        this.command = new ShowSuccessCommand();

    }},

    UNBLOCK_CLIENT {
        {
            this.command = new UnBlockClientCommand();
        }
    }

    ;
    public FrontControllerCommand command;

    public FrontControllerCommand getCommand() {
        return this.command;
    }
    }
