package com.portfolio.cms.Model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

```
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
private Integer id;

private String username;
private String email;
private String password;
private boolean admin = false;
private String profileImage;
private boolean verified = false;

@JsonManagedReference
@OneToMany(
    mappedBy = "author",
    cascade = CascadeType.ALL,
    orphanRemoval = true
)
private List<Content> contents = new ArrayList<>();

// Getters and Setters

public Integer getId() {
    return id;
}

public void setId(Integer id) {
    this.id = id;
}

public String getUsername() {
    return username;
}

public void setUsername(String username) {
    this.username = username;
}

public String getEmail() {
    return email;
}

public void setEmail(String email) {
    this.email = email;
}

public String getPassword() {
    return password;
}

public void setPassword(String password) {
    this.password = password;
}

public boolean isAdmin() {
    return admin;
}

public void setAdmin(boolean admin) {
    this.admin = admin;
}

public String getProfileImage() {
    return profileImage;
}

public void setProfileImage(String profileImage) {
    this.profileImage = profileImage;
}

public boolean isVerified() {
    return verified;
}

public void setVerified(boolean verified) {
    this.verified = verified;
}

public List<Content> getContents() {
    return contents;
}

public void setContents(List<Content> contents) {
    this.contents.clear();

    if (contents != null) {
        for (Content content : contents) {
            addContent(content);
        }
    }
}

// -----------------------------
// User Account Logic
// -----------------------------

public boolean isAccountReady() {
    return verified
            && username != null
            && !username.trim().isEmpty()
            && email != null
            && !email.trim().isEmpty()
            && password != null
            && !password.trim().isEmpty();
}

public void verifyAccount() {
    this.verified = true;
}

public void promoteToAdmin() {
    this.admin = true;
}

public void removeAdminAccess() {
    this.admin = false;
}

public void updateProfile(
        String username,
        String email,
        String profileImage) {

    if (username != null && !username.trim().isEmpty()) {
        this.username = username.trim();
    }

    if (email != null && !email.trim().isEmpty()) {
        this.email = email.trim().toLowerCase();
    }

    if (profileImage != null && !profileImage.trim().isEmpty()) {
        this.profileImage = profileImage.trim();
    }
}

// -----------------------------
// Content Management Logic
// -----------------------------

public void addContent(Content content) {

    if (content == null) {
        return;
    }

    if (!contents.contains(content)) {
        contents.add(content);
    }

    if (content.getAuthor() != this) {
        content.setAuthor(this);
    }
}

public void removeContent(Content content) {

    if (content == null) {
        return;
    }

    if (contents.remove(content)) {
        content.setAuthor(null);
    }
}

public boolean hasContent(Content content) {
    return content != null && contents.contains(content);
}

public int getContentCount() {
    return contents.size();
}
```

}
