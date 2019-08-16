package com.scut.se.sehubbackend.dao.user;

import com.scut.se.sehubbackend.domain.user.UserAuthentication;

public interface OwnerOnly{
    UserAuthentication getOwner();
}
