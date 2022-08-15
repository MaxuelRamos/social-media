insert into "user"(username)
values
('user 1'),
('user 2'),
('user 3'),
('user 4'),
('user 5');

insert into post (message, timestamp, reference_post_id, author_id)
values
('This is an original post', now(), null, 1),
('This is another original post', now(), null, 2),
(null, now(), 1, 3),
('This is a quote post', now(), 2, 4);