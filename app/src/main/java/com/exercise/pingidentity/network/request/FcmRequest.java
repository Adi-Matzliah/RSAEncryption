package com.exercise.pingidentity.network.request;

public class FcmRequest {

    final MessageData data;
    final String to;

    public FcmRequest(String regToken, String encryptedString, String signature) {
        data = new MessageData(encryptedString, signature);
        this.to = regToken;
    }

    private static class MessageData {
        final String encryptedString;
        final String signature;

        MessageData(String encryptedString, String signature) {
            this.encryptedString = encryptedString;
            this.signature = signature;
        }
    }
}