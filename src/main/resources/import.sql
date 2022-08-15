insert into "user"(username, date_joined)
values
('user 1', now()),
('user 2', now()),
('user 3', now()),
('user 4', now()),
('user 5', now());

insert into post (message, timestamp, reference_post_id, author_id)
values
('This is an original post', now(), null, 1),
('This is another original post', now(), null, 2),
(null, now(), 1, 3),
('This is a quote post', now(), 2, 4);