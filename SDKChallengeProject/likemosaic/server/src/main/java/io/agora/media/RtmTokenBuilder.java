package io.agora.media;

import io.agora.media.AccessToken;

public class RtmTokenBuilder {
    public enum Role {
        Rtm_User(1);

        int value;

        Role(int value) {
            this.value = value;
        }
    }

    public AccessToken mTokenCreator;

    public String buildToken(String appId, String appCertificate,
                             String uid, Role role, int privilegeTs) {
        mTokenCreator = new AccessToken(appId, appCertificate, uid, "");
        mTokenCreator.addPrivilege(AccessToken.Privileges.kRtmLogin, privilegeTs);
        try {
            return mTokenCreator.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public void setPrivilege(AccessToken.Privileges privilege, int expireTs) {
        mTokenCreator.addPrivilege(privilege, expireTs);
    }

    public boolean initTokenBuilder(String originToken) {
        mTokenCreator.fromString(originToken);
        return true;
    }
}