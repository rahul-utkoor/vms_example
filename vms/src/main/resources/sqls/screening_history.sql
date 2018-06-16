INSERT INTO `vms`.`volunteer_screening_history`
(`id`,`created`,`updated`,`updated_by`,`created_by`,`screening_date`,
`status_master_id`,`volunteer_id`,`crf_comments`,
`reviewd_date`,`reviewd_by`,`reviewer_comments`,`sample_comments`)
select ss.screening_status_id,ss.screening_date,ss.screening_date,ss.userName,ss.userName,ss.screening_date,
ss.screening_status,vm.volunteerstrid
,ss.crf_comments,ss.reviewed_date
,ss.reviewedBy,ss.reviewer_comments,ss.sample_comments from screening_status ss,volunteer_master vm where vm.volunteerid=ss.volunteerid


--
INSERT INTO `volunteer_status`
(`created`,`updated`,`updated_by`,`created_by`,
`status_master_id`,`volunteer_registration_number`,`screening_id`)
select ss.screening_date,ss.screening_date,ss.userName,ss.userName,
ss.screening_status,vm.volunteerstrid,ss.screening_status_id
 from screening_status ss,volunteer_master vm where vm.volunteerid=ss.volunteerid;
