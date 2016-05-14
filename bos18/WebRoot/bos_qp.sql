/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/10/19 9:01:08                           */
/*==============================================================*/


drop table if exists qp_noticebill;

drop table if exists qp_workbill;

drop table if exists qp_workordermanage;

/*==============================================================*/
/* Table: qp_noticebill                                         */
/*==============================================================*/
create table qp_noticebill
(
   id                   varchar(32) not null,
   staff_id             varchar(32),
   customer_id          varchar(32),
   customer_name        varchar(20),
   delegater            varchar(20),
   telephone            varchar(20),
   pickaddress          varchar(200),
   arrivecity           varchar(20),
   product              varchar(20),
   pickdate             date,
   num                  int,
   weight               double,
   volume               varchar(20),
   remark               varchar(255),
   ordertype            varchar(20),
   user_id              varchar(32),
   primary key (id)
);

/*==============================================================*/
/* Table: qp_workbill                                           */
/*==============================================================*/
create table qp_workbill
(
   id                   varchar(32) not null,
   noticebill_id        varchar(32),
   type                 varchar(20),
   pickstate            varchar(20),
   buildtime            timestamp,
   attachbilltimes      int,
   remark               varchar(255),
   staff_id             varchar(32),
   primary key (id)
);

/*==============================================================*/
/* Table: qp_workordermanage                                    */
/*==============================================================*/
create table qp_workordermanage
(
   id                   varchar(32) not null,
   arrivecity           varchar(20),
   product              varchar(20),
   num                  int,
   weight               double,
   floadreqr            varchar(255),
   prodtimelimit        varchar(40),
   prodtype             varchar(40),
   sendername           varchar(20),
   senderphone          varchar(20),
   senderaddr           varchar(200),
   receivername         varchar(20),
   receiverphone        varchar(20),
   receiveraddr         varchar(200),
   feeitemnum           int,
   actlweit             double,
   vol                  varchar(20),
   managerCheck         varchar(1),
   updatetime           date,
   primary key (id)
);

alter table qp_noticebill add constraint FK_Reference_2 foreign key (user_id)
      references t_user(id) on delete restrict on update restrict;

alter table qp_noticebill add constraint FK_Reference_3 foreign key (staff_id)
      references bc_staff(id) on delete restrict on update restrict;

alter table qp_workbill add constraint FK_Reference_4 foreign key (staff_id)
      references bc_staff(id) on delete restrict on update restrict;

alter table qp_workbill add constraint FK_workbill_noticebill_fk foreign key (noticebill_id)
      references qp_noticebill (id) on delete restrict on update restrict;

