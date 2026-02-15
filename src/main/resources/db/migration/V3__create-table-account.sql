create table if not exists account (
	id serial not null,
	primary key(id),
	user_id int not null,
	description varchar(200),
	bank_id int not null,
	initial_balance decimal(18,2) default 0,
	balance decimal(18,2) default 0,
	active boolean default true,

	constraint fk_account_user_id
	foreign key(user_id)
	references users(id),

	constraint fk_account_bank_id
	foreign key(bank_id)
	references bank(id)
)
