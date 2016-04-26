# CCAT Requirements

## General

* Title: Critical Care Audit Tool

* Splash screen and can really be any picture, here are some ideas:

* Background picture can be the first stethoscope picture (or something similar).

* Colour scheme should revolve around various blues, black, and white.

* The following logo should be incorporated on the title page:

![Alt text](https://github.com/ElliottSobek/CCAT/blob/master/specs/greyNunsLogo.png "Grey Nuns")

Two actions directions on the main menu:
1. Registered Nurses
2. Management

## Nurse
Nurses will have two buttons:

* An “About” section that includes the following:

    * About: The Critical Care Audit Tool (CCAT) is an evidenced-based, self-educating audit tool for Registered Nurses of the ICU and CCU units at the Grey Nuns Hospital to increase their knowledge in documentation while increasing their patient care competencies, care planning, and professional practice. 

    * Aim: To meet the needs of high acuity patients, accreditation standards, best practice standards, and continuing competency requirements, all while meeting the greater goal of improving quality of care and subsequently, patient safety.

* Has “Complete the Audit” button that leads to the audit form that can be submitted at the end.

## Admin
To get to the admin page ("File" > "Admin Tasks")

    * This would be a collection of all the audit results as well as an easy display of data analysis; it would be preferred if the user would have to enter a passcode to ensure confidentiality and sole management usage

    * This can be in the form of charts, etc. with percentages of yes/no/not applicable  answers from each question

    * Display how many audits have been submitted 

    * Have the ability to review each audit individually (on audits with a percentage of 100% display the Braden Scale score only)
    
    * Have the ability to delete old, completed audit forms

    * View the final percentage scores of the cumulative overall percentage score (e.g. one audit had an overall score of 85%; what is the cumulative score for all audits submitted? (daily, monthly, quarterly)) (percentages are calculated for answers of "yes", an audit form with a ratio of 3:4 "yes" answers will receive a score of 75%)

## User Stories
As a registered nurse I want to perform a critical care audit so that I can inspect the level critical care that was administered.

As a nurse practitioner I want to view a collection of audits so that I can have an easy display of all audits.

As a nurse practitioner I want to review individual audits so that I can view it's details.

As a nurse practitioner I want to view the average final percentage score from a collection of audits so that I can assess the overal level of critical care given.

## Provided Mocks

![Alt text](https://github.com/ElliottSobek/CCAT/blob/master/specs/mock1.png "Mock 1")

![Alt text](https://github.com/ElliottSobek/CCAT/blob/master/specs/mock2.png "Mock 2")
