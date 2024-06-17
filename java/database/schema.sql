BEGIN TRANSACTION;

--remember to drop tables in the order of MOST DEPENDENT to LEAST DEPENDENT

DROP TABLE IF EXISTS campaign_tag;
DROP TABLE IF EXISTS tag;
DROP TABLE IF EXISTS spend_request;
DROP TABLE IF EXISTS donations;
DROP TABLE IF EXISTS campaigns;
DROP TABLE IF EXISTS users;

--DROP TABLE IF EXISTS spend_campaign;


CREATE TABLE users (
   user_id SERIAL,
   username varchar(50) NOT NULL UNIQUE,
   password_hash varchar(200) NOT NULL,
   role varchar(50) NOT NULL,
   CONSTRAINT PK_user PRIMARY KEY (user_id)
);

CREATE TABLE campaigns (
    campaign_id SERIAL PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    end_date DATE NOT NULL,
    goal DECIMAL(10, 2) NOT NULL,
    manager_id INTEGER NOT NULL,
    image_url VARCHAR(255),
    funding DECIMAL (10, 2) DEFAULT 0,
    description VARCHAR(500),
    is_public BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (manager_id) REFERENCES users(user_id)
);

CREATE TABLE donations (
    donation_id SERIAL PRIMARY KEY,
    donor_id INTEGER NOT NULL,
    campaign_id INTEGER NOT NULL,
    donation_amount DECIMAL(10, 2) NOT NULL,
    donation_date_time TIMESTAMP NOT NULL,
    FOREIGN KEY (donor_id) REFERENCES users(user_id),
    FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)

);
CREATE TABLE tag (
tag_id SERIAL PRIMARY KEY,
tag_description VARCHAR (50) NOT NULL
);

CREATE TABLE campaign_tag (
tag_id INTEGER NOT NULL,
campaign_id INTEGER NOT NULL,
CONSTRAINT fk_tag FOREIGN KEY (tag_id) REFERENCES tag(tag_id),
CONSTRAINT fk_campaigns FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id),
PRIMARY KEY (tag_id, campaign_id)
);

CREATE TABLE spend_request (
spend_request_id SERIAL PRIMARY KEY,
campaign_id INTEGER NOT NULL,
spend_description VARCHAR(500),
spend_status INTEGER NOT NULL,
CONSTRAINT fk_campaign_id FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id)
);

--CREATE TABLE spend_campaign (
--spend_request_id INTEGER NOT NULL,
--campaign_id INTEGER NOT NULL,
--CONSTRAINT fk_spend_request FOREIGN KEY (spend_request_id) REFERENCES spend_request(spend_request_id),
--CONSTRAINT fk_campaign_id FOREIGN KEY (campaign_id) REFERENCES campaigns(campaign_id),
--PRIMARY KEY (spend_request_id, campaign_id)
--);
COMMIT TRANSACTION;