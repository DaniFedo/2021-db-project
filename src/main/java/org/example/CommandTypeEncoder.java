package org.example;

import lombok.Getter;

public class CommandTypeEncoder {
    static public final int
            PRODUCT = 1,
            PRODUCT_EXTENDED = 2;

    static public final int
            CREATE = 4,

    READ = 8,

    UPDATE = 16,

    DELETE = 32,

    LIST_BY_CRITERIA = 64;

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
        return INCOMING_COMMAND_TYPE ^ (IS_PRODUCT ? PRODUCT : PRODUCT_EXTENDED);
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

            case LIST_BY_CRITERIA:
                return "LIST_BY_CRITERIA";

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
