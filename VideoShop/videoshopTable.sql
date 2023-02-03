drop table customerTab;
drop table videoTab;
drop table rentTab;
CREATE	TABLE  customerTab
(	
	custTel		varchar2(20),
	custName	varchar2(20),
	custTelAid	varchar2(20),
	custAddr	varchar2(50),
	custEmail	varchar2(20)
);

ALTER	TABLE	customerTab
	ADD	CONSTRAINT  custTel_pk		PRIMARY KEY	(custTel );


CREATE	TABLE	videoTab
(
	videoNum	number,
	videoJanre	varchar2(20),
	videoTitle	varchar(50),
	videoDirector	 varchar2(30),
	videoActor	varchar2(50),
	videoContent	varchar2(1024),
	videoRegDate	date
);

ALTER	TABLE	videoTab
	ADD	CONSTRAINT  videoNum_pk		PRIMARY KEY	(videoNum);


CREATE	SEQUENCE		videoNumSeq;


CREATE	TABLE	rentTab
(
	rentNum			number,
	rentCustTel		varchar2(20),
	rentCustName		varchar2(20),
	rentvideoNum		number,
	rentDate		date,
	returnScheduled		date,
	returnDate		date,
	returnFlag		varchar2(5),
	rentCharge		number
);

ALTER	TABLE	rentTab
	ADD	CONSTRAINT  rentNum_pk		PRIMARY KEY	( rentNum );


ALTER	TABLE	rentTab
	ADD	CONSTRAINT  videoNum_fk	FOREIGN  KEY  ( rentvideoNum)
	REFERENCES  videoTab ( videoNum);

ALTER	TABLE	rentTab
	ADD	CONSTRAINT  custTel_fk	FOREIGN  KEY  ( rentCustTel )
	REFERENCES  customerTab ( custTel);


CREATE SEQUENCE		rentNumSeq;