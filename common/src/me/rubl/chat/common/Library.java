package me.rubl.chat.common;

public class Library {

    public static class Message {

        public enum MessageType {
            AUTH_REQUEST,
            AUTH_ACCEPT,
            AUTH_DENIED,
            MSG_BROADCAST,
            MSG_FORMAT_ERROR,
            UNKNOWN
        }

        public static final int PREFIX_INDEX = 0;
        public static final int AUTH_REQUEST_LENGTH = 3;
        public static final String DELIMITER = "±";
        public static final String AUTH_REQUEST_PREFIX = "/auth_request";
        public static final String AUTH_ACCEPT_PREFIX = "/auth_accept";
        public static final String AUTH_DENIED_PREFIX = "/auth_denied";
        public static final String MSG_FORMAT_ERROR_PREFIX = "/msg_format_error";
        // если мы вдруг не поняли, что за сообщение и не смогли разобрать
        public static final String MSG_BROADCAST_PREFIX = "/bcast";
        // то есть сообщение, которое будет посылаться всем

        public static MessageType getMessageType(String msg) {
            switch (msg.split(DELIMITER)[PREFIX_INDEX]) {
                case AUTH_REQUEST_PREFIX:
                    return MessageType.AUTH_REQUEST;
                case AUTH_ACCEPT_PREFIX:
                    return MessageType.AUTH_ACCEPT;
                case AUTH_DENIED_PREFIX:
                    return MessageType.AUTH_DENIED;
                case MSG_FORMAT_ERROR_PREFIX:
                    return MessageType.MSG_FORMAT_ERROR;
                case MSG_BROADCAST_PREFIX:
                    return MessageType.MSG_BROADCAST;
                default:
                    return MessageType.UNKNOWN;
            }
        }

        public static String getMessageBody(String msg) {
            String[] splittedMsg = msg.split(DELIMITER);

            /*
                /auth_request±login±password
                /auth_accept±nickname
                /auth_denied±msg
                /broadcast±msg
                /msg_format_error±msg
            * */

            switch (splittedMsg[PREFIX_INDEX]) {
                case AUTH_ACCEPT_PREFIX:
                    return "Authorized successfully";
                case AUTH_DENIED_PREFIX:
                case MSG_FORMAT_ERROR_PREFIX:
                    return splittedMsg[1];
                case MSG_BROADCAST_PREFIX:
                    return splittedMsg[3];
                default:
                    return "";
            }
        }

        public static long getMessageTime(String msg) {
            String[] splittedMsg = msg.split(DELIMITER);

            if (splittedMsg[PREFIX_INDEX].equals(MSG_BROADCAST_PREFIX)) {
                return Long.parseLong(splittedMsg[1]);
            } else return 0;
        }

        public static String getMessageAuthor(String msg) {
            String[] splittedMsg = msg.split(DELIMITER);

            if (splittedMsg[PREFIX_INDEX].equals(MSG_BROADCAST_PREFIX)) {
                return splittedMsg[2];
            } else {
                return "Server";
            }
        }

        public static String formatForAuthDenied(String msg) {
            return AUTH_DENIED_PREFIX + DELIMITER + msg;
        }

        public static String formatForAuthRequest(String login, String password) {
            return AUTH_REQUEST_PREFIX + DELIMITER + login + DELIMITER + password;
        }

        public static String formatForAuthAccept(String nickname) {
            return AUTH_ACCEPT_PREFIX + DELIMITER + nickname;
        }

        public static String formatForMsgFormatError(String message) {
            return MSG_FORMAT_ERROR_PREFIX + DELIMITER + message;
        }

        public static String formatForBroadcastMessage(String src, String message) {
            return MSG_BROADCAST_PREFIX + DELIMITER + System.currentTimeMillis() +
                    DELIMITER + src + DELIMITER + message;
        }
    }
}
