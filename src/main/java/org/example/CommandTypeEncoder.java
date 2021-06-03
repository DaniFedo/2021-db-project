package org.example;

import lombok.Getter;

public class CommandTypeEncoder {
    static public final int
            PRODUCT = 1,
            GROUP = 2;

    static public final int
            //add an amount of product(3) OR add a group(4)
            CREATE           = 4,

            //read an amount of product(1) OR *something new for future labs*
            READ             = 8,

            //update the price on the product(6) OR update name of product on the group(5)
            UPDATE           = 16,

            //delete an amount of product(2) OR *something new for future labs*
            DELETE           = 32;

    static public final int
            PRODUCT_CREATE           = PRODUCT ^ CREATE,
            PRODUCT_READ             = PRODUCT ^ READ,
            PRODUCT_UPDATE           = PRODUCT ^ UPDATE,
            PRODUCT_DELETE           = PRODUCT ^ DELETE;

    static public final int
            GROUP_CREATE           = GROUP ^ CREATE,
            GROUP_READ             = GROUP ^ READ,
            GROUP_UPDATE           = GROUP ^ UPDATE,
            GROUP_DELETE           = GROUP ^ DELETE;

    public CommandTypeEncoder(int INCOMING_COMMAND_TYPE) throws Exception {
        isProduct = isProduct(INCOMING_COMMAND_TYPE);
        commandTypeCode = getTypeCommandCode(INCOMING_COMMAND_TYPE);
        commandType = getTypeCommand(INCOMING_COMMAND_TYPE);
    }


    //checks whether command is used with product or not
    public static boolean isProduct(int INCOMING_COMMAND_TYPE) {
        return (INCOMING_COMMAND_TYPE & PRODUCT) == 1;
    }

    //shows original command code
    static int getTypeCommandCode(int INCOMING_COMMAND_TYPE) {
        boolean IS_PRODUCT = isProduct(INCOMING_COMMAND_TYPE);
        return INCOMING_COMMAND_TYPE ^ (IS_PRODUCT ? PRODUCT : GROUP);
    }

    //shows original command name
    static String getTypeCommand(int INCOMING_COMMAND_TYPE) throws Exception {
        int COMMAND = getTypeCommandCode(INCOMING_COMMAND_TYPE);
        switch (COMMAND) {
            case CREATE:
                return "CREATE";

            case READ:
                return "READ";

            case UPDATE:
                return "UPDATE";

            case DELETE:
                return "DELETE";

            /* for future
            case LIST_BY_CRITERIA:
                return "LIST_BY_CRITERIA";*/

            default:
                throw new Exception("Undefined INCOMING_COMMAND_TYPE");
        }
    }

    @Getter
    final boolean isProduct;

    @Getter
    final int commandTypeCode;

    @Getter
    String commandType;
}
