insert
into
    USERS (email,
           name,
           password,
           role,
           status)
select 'lab@gmail.com',
       'Lab',
       '$2a$10$ze2T2cQAxRjql2kXEwzSZux4UMTlt/4bP.Ma/oTdekyNOqpWAUB9C',
       3,
       1 where not exists(select 1 from users where email = 'lab@gmail.com');