// Copyright (c) Microsoft Corporation. All rights reserved.

var microsoft = microsoft || {};
microsoft.support = microsoft.support || {};
microsoft.support.signInHandler = microsoft.support.signInHandler || {};

// This script runs in a hidden iFrame and calls the parent window to let it know
// when a sign in has completed. Since sign-in happens on the server, a server
// generated razor view is used to determine which of these overloads gets called
// based on the sign in status detected by the iFrame. See Views/Home/SilentAuth.cshtml
(function (myself) {
    myself.userSignedIn = function(user)
    {
        parent.window.microsoft.support.config.authInfo.userSignedIn(user);
    }

    myself.userSignedOut = function(user)
    {
        parent.window.microsoft.support.config.authInfo.userSignedOut();
    }
})(microsoft.support.signInHandler);