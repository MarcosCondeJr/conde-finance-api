create table if not exists transactions (
	id serial not null,
	primary key(id),
	account_id int not null,
	category_id int not null,
	amount decimal(18,2) not null,
	transaction_date date not null,
	transaction_type varchar(20) not null,
	payment_method varchar(20),
	description varchar(200),

	constraint fk_transactions_account_id
	foreign key(account_id)
	references account(id),

	constraint fk_transactions_category_id
	foreign key(category_id)
	references category(id)
)