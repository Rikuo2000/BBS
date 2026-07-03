CREATE TABLE users (
    id BIGINT NOT NULL AUTO_INCREMENT,
    password VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    PRIMARY KEY (id),
    UNIQUE KEY UKr43af9ap4edm43mmtq01oddj6 (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE post (
    id BIGINT NOT NULL AUTO_INCREMENT,
    content VARCHAR(255) DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    title VARCHAR(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    user_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FK7ky67sgi7k0ayf22652f7763r (user_id),
    CONSTRAINT FK7ky67sgi7k0ayf22652f7763r
        FOREIGN KEY (user_id) REFERENCES users (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE comment (
    id BIGINT NOT NULL AUTO_INCREMENT,
    content VARCHAR(255) DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    post_id BIGINT DEFAULT NULL,
    user_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FKs1slvnkuemjsq2kj4h3vhx7i1 (post_id),
    KEY FKqm52p1v3o13hy268he0wcngr5 (user_id),
    CONSTRAINT FKqm52p1v3o13hy268he0wcngr5
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKs1slvnkuemjsq2kj4h3vhx7i1
        FOREIGN KEY (post_id) REFERENCES post (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE likes (
    id BIGINT NOT NULL AUTO_INCREMENT,
    created_at DATETIME(6) NOT NULL,
    post_id BIGINT DEFAULT NULL,
    user_id BIGINT DEFAULT NULL,
    PRIMARY KEY (id),
    KEY FKowd6f4s7x9f3w50pvlo6x3b41 (post_id),
    KEY FKnvx9seeqqyy71bij291pwiwrg (user_id),
    CONSTRAINT FKnvx9seeqqyy71bij291pwiwrg
        FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT FKowd6f4s7x9f3w50pvlo6x3b41
        FOREIGN KEY (post_id) REFERENCES post (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;