This is the group project with team members: Shangde Han, Tao Li
changes:
	We debugged it and found that in Search method we did not return 
null if we cannot find this node. 
	Insert method rotation parts are too complex to see where has 
problems. So, we decided to move rotation parts to help function and call it
in insert method. Original submission has some small problems when we link 
new node with its parents and we fixed it. Then, all testcases can run normally,