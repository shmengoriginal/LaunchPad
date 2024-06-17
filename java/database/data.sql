BEGIN TRANSACTION;

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

INSERT INTO users (username,password_hash,role) VALUES ('jdoe1', 'hash1', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jdoe2', 'hash2', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jdoe3', 'hash3', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jdoe4', 'hash4', 'ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('jdoe5', 'hash5', 'ROLE_USER');


INSERT INTO campaigns (title, end_date, goal, manager_id, image_url, funding, description, is_public) VALUES ('Campaign 1', '2024-12-31', 10000.00, 3, '', 0.00, 'Some description', true);
INSERT INTO campaigns (title, end_date, goal, manager_id, image_url, funding, description, is_public) VALUES ('Campaign 2', '2024-12-31', 20000.00, 4, '', 0.00, 'Another description', true);
INSERT INTO campaigns (title, end_date, goal, manager_id, image_url, funding, description, is_public) VALUES ('Campaign 3', '2024-12-31', 30000.00, 5, '', 0.00, 'Balh blah blah lbah lha', false);
INSERT INTO campaigns (title, end_date, goal, manager_id, image_url, funding, description, is_public) VALUES ('Campaign 4', '2024-12-31', 40000.00, 6, '', 0.00, 'ADFHgh ADNJKASBb f fbasjfkba dasd', true);
INSERT INTO campaigns (title, end_date, goal, manager_id, image_url, funding, description, is_public) VALUES ('Campaign 5', '2024-12-31', 50000.00, 7, '', 0.00, 'Lorem Ip[sum I forget the rest', true);


INSERT INTO donations (donor_id, campaign_id, donation_amount, donation_date_time) VALUES (1, 1, 50.00, '2024-01-01 12:00:00');
INSERT INTO donations (donor_id, campaign_id, donation_amount, donation_date_time) VALUES (2, 2, 75.00, '2024-01-02 13:00:00');
INSERT INTO donations (donor_id, campaign_id, donation_amount, donation_date_time) VALUES (3, 3, 100.00, '2024-01-03 14:00:00');
INSERT INTO donations (donor_id, campaign_id, donation_amount, donation_date_time) VALUES (4, 4, 125.00, '2024-01-04 15:00:00');
INSERT INTO donations (donor_id, campaign_id, donation_amount, donation_date_time) VALUES (5, 5, 150.00, '2024-01-05 16:00:00');

INSERT INTO tag (tag_description) VALUES ('health');
INSERT INTO tag (tag_description) VALUES ('environment');
INSERT INTO tag (tag_description) VALUES ('school');
INSERT INTO tag (tag_description) VALUES ('game');

INSERT INTO spend_request (campaign_id, spend_description, spend_status) VALUES (1, 'do something cool', 0);
INSERT INTO spend_request (campaign_id, spend_description, spend_status) VALUES (1, 'do something crazy', 1);
INSERT INTO spend_request (campaign_id, spend_description, spend_status) VALUES (2, 'do something in general', 2);

INSERT INTO campaign_tag (tag_id, campaign_id) VALUES (1,1);
INSERT INTO campaign_tag (tag_id, campaign_id) VALUES (1,4);
INSERT INTO campaign_tag (tag_id, campaign_id) VALUES (4,3);
INSERT INTO campaign_tag (tag_id, campaign_id) VALUES (2,1);
INSERT INTO campaign_tag (tag_id, campaign_id) VALUES (3,2);


COMMIT TRANSACTION;
