#include "stdafx.h"  //Used in visual Studio
#include<iostream>   // I/O header files declaration
#include<omp.h>      //open mp library
#include<stdlib.h>   //for memory allocation
#include<random>     //for randon number generation


using namespace std;

#define NUMBER_OF_THREAD 4

double** a;
double** b;
double** c;

double T_Starting;
double T_Stopping;

int Aa;
int Ab;
int Ba;
int Bb;

void           Matrix_Get();
void            Serial_Mat_Multi();
void            Parallel_Mat_Multi();



int main()
{
	printf("Matrix Multiplication Program\n\n");
	cout << "Enter Size of Matrix A in the form of rows and columns: ";
	cin >> Aa >> Ab;
	cout << "Enter Size of Matrix B in the form of rows and columns:: ";
	cin >> Ba >> Bb;

	Matrix_Get();    				
	Serial_Mat_Multi(); //calling
	Parallel_Mat_Multi();




	system("pause");
	return 0;

}
void Matrix_Get()
{
	a = new double*[Aa];
	b = new double*[Ba];
	c = new double*[Aa];
	for (int i = 0; i<Aa; i++){ a[i] = new double[Ab]; }
	for (int i = 0; i<Ba; i++){ b[i] = new double[Bb]; }
	for (int i = 0; i<Aa; i++){ c[i] = new double[Bb]; }

	for (int i = 0; i<Aa; i++)
	{
		for (int j = 0; j<Ab; j++)
		{
			a[i][j] = rand() % 10 + 1;
		}
	}

	for (int i = 0; i<Ba; i++)
	{
		for (int j = 0; j<Bb; j++)
		{
			b[i][j] = rand() % 10 + 1;
		}
	}
	printf("Matrix Multiplication Create is Completed.\n");
}


void Serial_Mat_Multi()
{
	
	T_Starting = omp_get_wtime(); //returns time gone in seconds
	for (int i = 0; i<Aa; i++)
	{
		for (int j = 0; j<Bb; j++)
		{
			double temp = 0;
			for (int k = 0; k<Ab; k++)
			{
				temp += a[i][k] * b[k][j];
			}
		}
	}
	T_Stopping = omp_get_wtime();
	cout << "Matrix Multiplication Time for Serial: " << T_Stopping - T_Starting << " seconds" << endl;
}


void Parallel_Mat_Multi()
{
	int i, j, k;
	T_Starting = omp_get_wtime();

	omp_set_num_threads(NUMBER_OF_THREAD);  //number of threads running parallely
#pragma omp parallel for schedule(dynamic,50) private(i,j,k) shared(a,b,c) //taking a group of threads
	for (i = 0; i<Aa; i++)
	{
		for (j = 0; j<Bb; j++)
		{
			double temp = 0;
			for (k = 0; k<Ab; k++)
			{
				c[i][j] += a[i][k] * b[k][j];
			}
		}
	}

	T_Stopping = omp_get_wtime();
		
	cout << "Matrix Multiplication Time for Parallel: " << T_Stopping- T_Starting<< " seconds." << endl;
}








