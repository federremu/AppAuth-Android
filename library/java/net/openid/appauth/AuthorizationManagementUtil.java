/*
 * Copyright 2015 The AppAuth for Android Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.openid.appauth;

import static net.openid.appauth.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.util.Base64;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;

class AuthorizationManagementUtil {
    private static final int STATE_LENGTH = 16;

    static String generateRandomState() {
        SecureRandom sr = new SecureRandom();
        byte[] random = new byte[STATE_LENGTH];
        sr.nextBytes(random);
        return Base64.encodeToString(random, Base64.NO_WRAP | Base64.NO_PADDING | Base64.URL_SAFE);
    }

    /**
     * Reads an authorization request from a JSON string representation produced by either
     * {@link AuthorizationRequest#jsonSerialize()} or {@link EndSessionRequest#jsonSerialize()}.
     * @throws JSONException if the provided JSON does not match the expected structure.
     */
    static AuthorizationManagementRequest requestFrom(String jsonStr)
            throws JSONException {
        checkNotNull(jsonStr, "jsonStr can not be null");

        JSONObject json = new JSONObject(jsonStr);
        if (AuthorizationRequest.isAuthorizationRequest(json)) {
            return AuthorizationRequest.jsonDeserialize(json);
        }

        if (EndSessionRequest.isEndSessionRequest(json)) {
            return EndSessionRequest.jsonDeserialize(json);
        }

        throw new IllegalArgumentException(
            "No AuthorizationManagementRequest found matching to this json schema");
    }

    /**
     * Builds an AuthorizationManagementResponse from
     * {@link AuthorizationManagementRequest} and {@link Uri}
     */
    @SuppressLint("VisibleForTests")
    static AuthorizationManagementResponse responseWith(
            AuthorizationManagementRequest request, Uri uri) {
        if (request instanceof AuthorizationRequest) {//aca entra con https://openidconnect.net/callback?code=4c27ac85ce364b9591f68b539a32aa7f&state=1KxMLpSTm7c6XlhRkNHkbA
            return new AuthorizationResponse.Builder((AuthorizationRequest) request)
                .fromUri(uri)
                .build();
        }
        if (request instanceof EndSessionRequest) {
            return new EndSessionResponse.Builder((EndSessionRequest) request)
                .fromUri(uri)
                .build();
        }
        throw new IllegalArgumentException("Malformed request or uri");
    }

    /**
     * Extracts response from an intent produced by {@link #toIntent()}. This is
     * used to extract the response from the intent data passed to an activity registered as the
     * handler for {@link AuthorizationService#performEndSessionRequest}
     * or {@link AuthorizationService#performAuthorizationRequest}.
     */
    @Nullable
    static AuthorizationManagementResponse responseFrom(@NonNull Intent dataIntent) {

        if (EndSessionResponse.containsEndSessionResponse(dataIntent)) {
            return EndSessionResponse.fromIntent(dataIntent);
        }

        if (AuthorizationResponse.containsAuthorizationResponse(dataIntent)) {
            return AuthorizationResponse.fromIntent(dataIntent);
        }

        throw new IllegalArgumentException("Malformed intent");
    }
}
