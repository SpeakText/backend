package com.speaktext.backend.auth.presentation.validator;

import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;

public class UrlSafetyValidator {

    private UrlSafetyValidator() {
    }

    public static void validate(String url) {
        URI uri = parseUri(url);
        validateScheme(uri);
        String host = extractHost(uri);
        InetAddress inetAddress = resolveHost(host);
        validatePublicIp(inetAddress);
    }

    private static URI parseUri(String url) {
        try {
            return new URI(url);
        } catch (Exception e) {
            throw new IllegalArgumentException("잘못된 URL 형식입니다.");
        }
    }

    private static void validateScheme(URI uri) {
        String scheme = uri.getScheme();
        if (scheme == null || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))) {
            throw new IllegalArgumentException("허용되지 않은 URL 스킴입니다.");
        }
    }

    private static String extractHost(URI uri) {
        String host = uri.getHost();
        if (host == null) {
            throw new IllegalArgumentException("호스트가 없는 URL입니다.");
        }
        return host;
    }

    private static InetAddress resolveHost(String host) {
        try {
            return InetAddress.getByName(host);
        } catch (UnknownHostException e) {
            throw new IllegalArgumentException("호스트를 찾을 수 없습니다.");
        }
    }

    private static void validatePublicIp(InetAddress address) {
        byte[] addr = address.getAddress();
        int first = Byte.toUnsignedInt(addr[0]);
        int second = Byte.toUnsignedInt(addr[1]);

        if (isPrivateIp(first, second)) {
            throw new IllegalArgumentException("내부망으로의 요청은 허용되지 않습니다.");
        }
    }

    private static boolean isPrivateIp(int first, int second) {
        return (first == 10) ||
                (first == 172 && (16 <= second && second <= 31)) ||
                (first == 192 && second == 168) ||
                (first == 127);
    }
}