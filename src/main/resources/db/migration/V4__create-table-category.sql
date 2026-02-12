create table if not exists category (
	id serial not null,
	primary key(id),
	user_id int not null,
	name varchar(200) not null,
	category_type varchar(20) not null,

	constraint fk_category_user_id
	foreign key(user_id)
	references users(id)
)