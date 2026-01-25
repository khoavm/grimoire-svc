package com.service.constant;

public enum QuestActionType {
    unknown, input_text, quiz, file_submission, voice_record;

    public static String name(QuestActionType type) {
        return type == null ? "" : type.name();
    }

    public static QuestActionType getVal(String type) {
        try {
            if (type == null) {
                return unknown;
            }
            return QuestActionType.valueOf(type);
        } catch (IllegalArgumentException e) {
            return unknown;
        }
    }
}
